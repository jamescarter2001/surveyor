import { TextField, Theme, Typography } from "@mui/material";
import { FC } from "react";
import { UseFormRegister } from "react-hook-form";

interface LabelledTextFieldProps {
  id?: string;
  label: string;
  onChange?: (value: string) => void;
  disabled?: boolean;
  register?: UseFormRegister<any>;
}

/**
 * Common labelled text field component used for taking user input.
 * @param id An optional string containing the ID of the component.
 * @param label A string containing the label text.
 * @param onChange An optional callback function to execute when the TextField component input is updated.
 * @param disabled An optional boolean dictating if the component is in a disabled state.
 * @param register An optional react-hook-form register to pass TextField updates to.
 * @returns A LabelledTextField React component.
 */
export const LabelledTextField: FC<LabelledTextFieldProps> = ({
  id,
  label,
  onChange,
  disabled,
  register,
}) => {
  return (
    <>
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
      <TextField
        label="Answer"
        data-testid={`textfield-${id}`}
        multiline
        rows={3}
        variant="outlined"
        onChange={(event) => onChange && onChange(event.target.value)}
        fullWidth
        disabled={disabled}
        {...(register && id
          ? register(id, { required: true, minLength: 1 })
          : undefined)}
      />
    </>
  );
};
