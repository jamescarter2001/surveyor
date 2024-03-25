import { fireEvent, render, screen } from "@testing-library/react";
import { LabelledTextField } from "./LabelledTextField";

const testLabel = "test label";

test("should render", () => {
  const onChange = jest.fn(() => {});

  render(<LabelledTextField id="1" label={testLabel} onChange={onChange} />);
  const label = screen.getByText(testLabel);
  expect(label).toBeInTheDocument();

  const textField = screen.getByRole("textbox");
  fireEvent.change(textField, { target: { value: "test" } });
  expect(onChange).toHaveBeenCalled();
});

test("should render in disabled state", () => {
  render(<LabelledTextField id="1" label={testLabel} disabled />);

  const textField = screen.getByRole("textbox");
  expect(textField).toBeDisabled();
});
