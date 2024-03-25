import {
  fireEvent,
  render,
  screen,
  waitFor,
  within,
} from "@testing-library/react";
import { SurveyModal } from "./SurveyModal";
import { Facility } from "@/types/Facility";
import { Survey } from "../../types/Survey";

const facility: Facility = {
  facilityId: 1,
  name: "test facility",
  description: "test description",
};

const survey: Survey = {
  surveyId: 1,
  name: "test survey",
  startTime: new Date("01 Jan 1970 00:00:00 UTC"),
  endTIme: new Date("01 Jan 2070 00:00:00 UTC"),
  connections: [
    {
      question: {
        questionId: 1,
        type: {
          questionTypeId: 1,
          name: "FreeText",
          options: [],
        },
        body: "test question free text",
      },
      priority: 1,
    },
    {
      question: {
        questionId: 2,
        type: {
          questionTypeId: 2,
          name: "LikertScale",
          options: [
            {
              questionTypeOptionId: 1,
              questionTypeId: 2,
              value: 1,
              alias: "Strongly Disagree",
            },
            {
              questionTypeOptionId: 2,
              questionTypeId: 2,
              value: 2,
              alias: "Strongly Agree",
            },
          ],
        },
        body: "test question agreement scale",
      },
      priority: 2,
    },
  ],
};

let fetchSpy: jest.SpyInstance;

beforeEach(() => {
  fetchSpy = jest
    .spyOn(global, "fetch")
    .mockImplementationOnce(
      jest.fn(() =>
        Promise.resolve({ json: () => Promise.resolve() }),
      ) as jest.Mock,
    );
});

afterEach(() => {
  jest.clearAllMocks();
});

test("should render", async () => {
  render(<SurveyModal open survey={survey} facility={facility} />);

  // Wait for initial render.
  await screen.findByText("Feedback Form");

  const header = screen.getByText("Feedback Form");
  const subtitle = screen.getByText(facility.name);

  expect(header).toBeInTheDocument();
  expect(subtitle).toBeInTheDocument();

  const questionText1 = screen.getByText("1. test question free text");
  const questionText2 = screen.getByText("2. test question agreement scale");

  expect(questionText1).toBeInTheDocument();
  expect(questionText2).toBeInTheDocument();

  const textField = screen.getByTestId("textfield-1");
  const textbox = within(textField).getByRole("textbox");

  const radiobutton = screen.getByTestId("rs-radiobutton-1");

  expect(textbox).toBeInTheDocument();
  expect(radiobutton).toBeInTheDocument();
});

test("should handle close", async () => {
  const onClose = jest.fn();
  render(
    <SurveyModal open survey={survey} facility={facility} onClose={onClose} />,
  );

  const button = await screen.findByTestId("sm-button-close");

  // Simulate the user clicking the close button.
  fireEvent.click(button);

  // Modal should have closed.
  await waitFor(() => {
    expect(onClose).toHaveBeenCalled();
  });
});

test("should allow submit when all questions answered", async () => {
  render(<SurveyModal open survey={survey} facility={facility} />);

  // Wait for initial render.
  await screen.findByText("Feedback Form");

  const button = screen.getByTestId("loadingbutton-button");

  // Shouldn't allow submission until all questions have been answered.
  expect(fetchSpy).not.toHaveBeenCalled();
  expect(button).toBeDisabled();

  const textField = screen.getByTestId("textfield-1");
  const textbox = within(textField).getByRole("textbox");

  // Simulate the user providing an answer for the free text question.
  fireEvent.change(textbox, { target: { value: "test" } });

  // Still shouldn't allow submission as only one question has been answered.
  expect(button).toBeDisabled();

  const radiobutton = screen.getByTestId("rs-radiobutton-1");

  // Simulate the user providing an answer for the agreement scale question.
  fireEvent.click(radiobutton, { target: { checked: true } });

  // Submission should now be allowed.
  await waitFor(() => {
    expect(button).toBeEnabled();
  });

  // Simulate the user attempting to submit their feedback.
  fireEvent.click(button);

  // Should have attempted to submit feedback to the backend.
  await waitFor(() => {
    expect(fetchSpy).toHaveBeenCalled();
  });
});
