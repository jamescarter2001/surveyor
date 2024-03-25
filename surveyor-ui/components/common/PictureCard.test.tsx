import { fireEvent, render, screen } from "@testing-library/react";
import { PictureCard } from "./PictureCard";

test("should render", () => {
  const onClick = jest.fn();

  render(
    <PictureCard
      title="title"
      subtitle="subtitle"
      content="content"
      image=""
      onClick={onClick}
    />,
  );

  const title = screen.getByText("title");
  const subtitle = screen.getByText("subtitle");
  const content = screen.getByText("content");

  expect(title).toBeInTheDocument();
  expect(subtitle).toBeInTheDocument();
  expect(content).toBeInTheDocument();

  const card = screen.getByTestId("pc-cardactionarea");
  fireEvent.click(card);

  expect(onClick).toHaveBeenCalled();
});
