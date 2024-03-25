import { FC, useState } from "react";
import { styled, Typography, Toolbar } from "@mui/material";
import ConnectingAirportsIcon from "@mui/icons-material/ConnectingAirports";
import AppBar from "./AppBar/AppBar";
import { AirportSelector } from "./Airport/AirportSelector";
import { FacilitySelector } from "./Facility/FacilitySelector";
import useFetch from "@/hooks/useFetch";
import { Airport } from "@/types/Airport";

interface AppProps {}

const ContentContainer = styled("div")(({ theme }) => ({
  padding: theme.spacing(2),
}));

/**
 * Contains the main app component structure that is presented to the user.
 * @returns The main App component.
 */
export const App: FC<AppProps> = () => {
  // Query the backend asynchronously using the custom hook.
  const { loading, data, error } = useFetch<readonly Airport[]>(
    process.env.SURVEYOR_BACKEND_URL + "/airports",
  );

  const [airport, setAirport] = useState<Airport | undefined>(undefined);

  return (
    <>
      <AppBar position="static" elevation={0}>
        <Toolbar variant="dense">
          <ConnectingAirportsIcon sx={{ paddingRight: 1 }} />
          <Typography variant="h6">EXETER AIRWAYS</Typography>
        </Toolbar>
      </AppBar>
      <ContentContainer>
        <AirportSelector
          airports={(data as readonly Airport[]) ?? []}
          loading={loading}
          error={error}
          onChange={setAirport}
        />
        <FacilitySelector airport={airport} />
      </ContentContainer>
    </>
  );
};
