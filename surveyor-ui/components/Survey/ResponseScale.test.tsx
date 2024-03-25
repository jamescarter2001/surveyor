import { fireEvent, render, screen } from "@testing-library/react";
import { ResponseScale } from "./ResponseScale";
import { QuestionTypeOption } from "@/types/QuestionTypeOption";

const options: QuestionTypeOption[] = [
  {
    questionTypeOptionId: 1,
    questionTypeId: 1,
    value: 1,
    alias: "Test Option 1",
  },
  {
    questionTypeOptionId: 2,
    questionTypeId: 1,
    value: 2,
    alias: "Test Option 2",
  },
];

test("should render", () => {
  const onChange = jest.fn();
  render(<ResponseScale options={options} onChange={onChange} />);

  const option1 = screen.getByText("Test Option 1");
  const option2 = screen.getByText("Test Option 2");

  expect(option1).toBeInTheDocument();
  expect(option2).toBeInTheDocument();

  const radio = screen.getByTestId("rs-radiobutton-1");

  fireEvent.click(radio);

  expect(onChange).toHaveBeenCalled();
});
