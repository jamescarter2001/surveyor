import { act, fireEvent, render, screen, within } from "@testing-library/react";
import { AirportSelector } from "./AirportSelector";
import { Airport } from "@/types/Airport";

const testData: Airport[] = [
  {
    airportId: "EXT",
    name: "Exeter Airport",
    lounges: [],
  },
  {
    airportId: "LHR",
    name: "London Heathrow Airport",
    lounges: [],
  },
];

test("should render", () => {
  render(<AirportSelector airports={testData} />);

  const button = screen.getByLabelText("Open");

  fireEvent.click(button);

  const lhrText = screen.queryByText("London Heathrow Airport");
  const extText = screen.queryByText("Exeter Airport");

  expect(lhrText).toBeInTheDocument();
  expect(extText).toBeInTheDocument();
});

test("should render when input given", () => {
  render(<AirportSelector airports={testData} />);

  const autocomplete = screen.getByTestId("as-autocomplete");
  const combobox = within(autocomplete).getByRole("combobox");

  act(() => {
    combobox.focus();
  });

  fireEvent.change(combobox, { target: { value: "london" } });

  const lhrText = screen.queryByText("London Heathrow Airport");
  const extText = screen.queryByText("Exeter Airport");
  expect(lhrText).toBeInTheDocument();
  expect(extText).not.toBeInTheDocument();
});
