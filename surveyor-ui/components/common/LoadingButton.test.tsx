import { fireEvent, render, screen } from "@testing-library/react";
import { LoadingButton } from "./LoadingButton";

test("should render", () => {
  const onClick = jest.fn();
  render(<LoadingButton onClick={onClick}>Test</LoadingButton>);

  const button = screen.getByText("Test");
  expect(button).toBeInTheDocument();
  expect(button).toBeEnabled();
  expect(
    screen.queryByTestId("loadingbutton-progress"),
  ).not.toBeInTheDocument();

  expect(onClick).not.toHaveBeenCalled();
  fireEvent.click(button);
  expect(onClick).toHaveBeenCalled();
});

test("should render in disabled state", () => {
  const onClick = jest.fn();
  render(
    <LoadingButton onClick={onClick} disabled>
      Test
    </LoadingButton>,
  );
  const button = screen.getByText("Test");

  expect(button).toBeDisabled();
  fireEvent.click(button);
  expect(onClick).not.toHaveBeenCalled();
});

test("should render in loading state", () => {
  render(<LoadingButton loading>Test</LoadingButton>);
  expect(screen.queryByTestId("loadingbutton-progress")).toBeInTheDocument();
});
