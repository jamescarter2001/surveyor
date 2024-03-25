import { Facility } from "./Facility";

/**
 * Represents an airport lounge.
 */
export interface AirportLounge {
  airportLoungeId: number;
  name: string;
  facilities: Facility[];
}
