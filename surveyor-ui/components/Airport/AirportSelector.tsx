import { FC, Fragment } from "react";
import {
  Autocomplete,
  Box,
  CircularProgress,
  FormControl,
  TextField,
  Theme,
  Typography,
} from "@mui/material";
import ErrorIcon from "@mui/icons-material/Error";
import { Airport } from "@/types/Airport";

interface AirportSelectorProps {
  airports: readonly Airport[];
  loading?: boolean;
  error?: string;
  onChange?: (airport: Airport) => void;
}

/**
 * Airport selector component used to present a list of selectable airport options.
 * @param airports A list of selectable airports.
 * @param loading An optional boolean dictating if the component is in a loading state.
 * @param error An optional boolean dictating if the component is in an error state.
 * @param onChange A callback function to execute when the airport selection is updated.
 * @returns An AirportSelector component.
 */
export const AirportSelector: FC<AirportSelectorProps> = ({
  airports,
  loading,
  error,
  onChange,
}) => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        paddingBottom: 2,
      }}
    >
      <FormControl
        sx={{
          minWidth: "20%",
          maxWidth: "30%",
          paddingRight: 2,
        }}
      >
        <Autocomplete
          disabled={loading || error != null}
          loading={loading}
          options={airports ?? []}
          getOptionLabel={(option) => option.name}
          onChange={(event, value) => {
            onChange && onChange(value as Airport);
          }}
          renderInput={(params) => (
            <TextField
              {...params}
              label="Airport"
              InputProps={{
                ...params.InputProps,
                endAdornment: (
                  <Fragment>
                    {loading && <CircularProgress color="inherit" size={20} />}
                    {error && <ErrorIcon />}
                    {params.InputProps.endAdornment}
                  </Fragment>
                ),
              }}
            />
          )}
          data-testid="as-autocomplete"
        />
      </FormControl>
      {error && (
        <Typography
          variant="subtitle2"
          sx={{ color: (theme: Theme) => theme.palette.text.secondary }}
        >
          {error}
        </Typography>
      )}
    </Box>
  );
};
