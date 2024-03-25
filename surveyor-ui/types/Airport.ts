import { AirportLounge } from "./AirportLounge";

/**
 * Represents an airport.
 */
export interface Airport {
  airportId: string;
  name: string;
  lounges: AirportLounge[];
}
