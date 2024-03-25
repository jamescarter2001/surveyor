import { FC } from "react";
import { Airport } from "../../types/Airport";
import { Grid, Typography } from "@mui/material";
import { FacilityCard } from "./FacilityCard";

type FacilitySelectorProps = {
  airport?: Airport;
};

/**
 * Facility selector component used to present selectable facility cards in a Material UI grid.
 * @param airport An optional airport object.
 * @returns a FacilitySelector react component.
 */
export const FacilitySelector: FC<FacilitySelectorProps> = ({ airport }) => {
  const lounges = airport?.lounges;

  const facilityCount = lounges?.map((l) => l.facilities).flat().length;
  const noLocationsFound: boolean = facilityCount === 0;

  return (
    <>
      {!airport && <Typography>Please select an airport.</Typography>}

      {noLocationsFound && (
        <Typography>No locations found for this airport.</Typography>
      )}

      <Grid container spacing={2} columns={4}>
        {lounges?.map((lounge) => {
          return lounge.facilities.map((facility) => {
            return (
              <Grid key={facility.facilityId} item xs={1}>
                <FacilityCard facility={facility} lounge={lounge} />
              </Grid>
            );
          });
        })}
      </Grid>
    </>
  );
};
