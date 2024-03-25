import { render, screen } from "@testing-library/react";
import { FacilityCard } from "./FacilityCard";
import { Facility } from "@/types/Facility";
import { AirportLounge } from "@/types/AirportLounge";
import { Survey } from "@/types/Survey";

const facility: Facility = {
  facilityId: 1,
  name: "Test Facility",
  description: "Test description",
};

const lounge: AirportLounge = {
  airportLoungeId: 1,
  name: "Test Lounge",
  facilities: [facility],
};

const survey: Survey = {
  surveyId: 1,
  name: "test survey",
  startTime: new Date("01 Jan 1970 00:00:00 GMT"),
  endTIme: new Date("01 Jan 2070 00:00:00 GMT"),
  connections: [],
};

test("should render", async () => {
  render(<FacilityCard facility={facility} lounge={lounge} />);

  // Wait for initial render.
  await screen.findByText("Test Facility");

  const title = screen.getByText("Test Facility");
  const subtitle = screen.getByText("Test Lounge");
  const description = screen.getByText("Test description");

  expect(title).toBeInTheDocument();
  expect(subtitle).toBeInTheDocument();
  expect(description).toBeInTheDocument();
});
