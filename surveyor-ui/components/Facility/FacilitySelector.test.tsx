import { render, screen, waitFor } from "@testing-library/react";
import { Airport } from "@/types/Airport";
import { FacilitySelector } from "./FacilitySelector";
import { Survey } from "@/types/Survey";

import "whatwg-fetch";

const airport: Airport = {
  airportId: "ABC",
  name: "Test Airport",
  lounges: [
    {
      airportLoungeId: 1,
      name: "Test Lounge",
      facilities: [
        {
          facilityId: 1,
          name: "Test Facility 1",
          description: "abcdefg",
        },
        {
          facilityId: 2,
          name: "Test Facility 2",
          description: "hijklmn",
        },
      ],
    },
  ],
};

const airportNoLounges: Airport = {
  airportId: "ABC",
  name: "Test Airport",
  lounges: [],
};

const airportNoFacilities: Airport = {
  airportId: "ABC",
  name: "Test Airport",
  lounges: [
    {
      airportLoungeId: 1,
      name: "Test Lounge",
      facilities: [],
    },
  ],
};

const survey: Survey = {
  surveyId: 1,
  name: "test survey",
  startTime: new Date("01 Jan 1970 00:00:00 UTC"),
  endTIme: new Date("01 Jan 2070 00:00:00 UTC"),
  connections: [],
};

afterEach(() => {
  jest.clearAllMocks();
});

test("should render", async () => {
  jest
    .spyOn(global, "fetch")
    .mockImplementation(
      jest.fn(() =>
        Promise.resolve({ json: () => Promise.resolve(survey) }),
      ) as jest.Mock,
    );

  render(<FacilitySelector airport={airport} />);

  const facility1 = await screen.findByText("Test Facility 1");
  const facility2 = await screen.findByText("Test Facility 2");

  expect(facility1).toBeInTheDocument();
  expect(facility2).toBeInTheDocument();
});

test("should render message if no airport given", async () => {
  render(<FacilitySelector />);

  const message = await screen.findByText("Please select an airport.");

  expect(message).toBeInTheDocument();
});

test("should render message if no lounges exist", async () => {
  render(<FacilitySelector airport={airportNoLounges} />);

  await waitFor(() => {
    expect(
      screen.getByText("No locations found for this airport."),
    ).toBeInTheDocument();
  });
});

test("should render message if no facilities exist", async () => {
  render(<FacilitySelector airport={airportNoFacilities} />);

  await waitFor(() => {
    expect(
      screen.getByText("No locations found for this airport."),
    ).toBeInTheDocument();
  });
});
