import {
  Box,
  FormControl,
  FormControlLabel,
  Radio,
  RadioGroup,
  Theme,
  Typography,
} from "@mui/material";
import { FC } from "react";
import { UseFormRegister } from "react-hook-form";
import { QuestionTypeOption } from "@/types/QuestionTypeOption";

type ScaleProps = {
  id?: string;
  label?: string;
  options: QuestionTypeOption[];
  disabled?: boolean;
  onChange?: (value: string) => void;
  register?: UseFormRegister<any>;
};

/**
 * Scale component used to render an arbirary number of question response options.
 * @param id An optional string containing the id of the component.
 * @param label An optional string containing the label for the scale.
 * @param options A list of QuestionTypeOption objects.
 * @param disabled A boolean dictating if the component is in a disabled state.
 * @param onChange An optional callback function to execute when the selected option is changed.
 * @param register An optional react-hook-form register to pass TextField updates to.
 * @returns a Scale react component.
 */
export const ResponseScale: FC<ScaleProps> = ({
  id,
  label,
  options,
  disabled,
  onChange,
  register,
}) => {
  return (
    <Box display="flex" flexDirection="column">
      <Typography
        variant="subtitle1"
        sx={{
          paddingBottom: 1,
          textAlign: "start",
          color: (theme: Theme) =>
            disabled ? theme.palette.text.disabled : theme.palette.text.primary,
        }}
      >
        {label}
      </Typography>
      <FormControl>
        <RadioGroup
          row
          sx={{ flexDirection: "row", justifyContent: "space-evenly" }}
          onChange={(event) => onChange && onChange(event.target.value)}
        >
          {options
            .sort((a, b) => a.value - b.value)
            .map((o) => (
              <FormControlLabel
                key={o.value}
                name={o.value.toString()}
                value={o.value}
                control={
                  <Radio
                    color="primary"
                    data-testid={`rs-radiobutton-${o.value}`}
                  />
                }
                label={o.alias ?? o.value}
                labelPlacement="bottom"
                sx={{ width: "130px" }}
                disabled={disabled}
                {...(register && id
                  ? register(id, { required: true })
                  : undefined)}
              />
            ))}
        </RadioGroup>
      </FormControl>
    </Box>
  );
};
