import { Box, Button, CircularProgress } from "@mui/material";
import { OverridableStringUnion } from "@mui/types";
import { FC, ReactNode } from "react";

interface LoadingButtonProps {
  children: ReactNode;
  type?: "submit" | "reset" | "button" | undefined;
  variant?: OverridableStringUnion<"text" | "outlined" | "contained">;
  onClick?: () => void;
  disabled?: boolean;
  loading?: boolean;
}

/**
 * Custom button component with loading state.
 * @param children A ReactNode containing the component children to render inside the button.
 * @param type The CSS type for the button.
 * @param variant A Material UI variant for the button (text, outlined, contained)
 * @param onClick A callback function to execute when the button is pressed.
 * @param disabled A boolean dictating if the component is in a disabled state.
 * @param loading A boolean dictating if the component is in a loading state.
 * @returns A LoadingButton React component.
 */
export const LoadingButton: FC<LoadingButtonProps> = ({
  children,
  type,
  variant,
  onClick,
  disabled,
  loading,
}) => {
  return (
    <Button
      variant={variant}
      disabled={disabled}
      onClick={onClick}
      type={type}
      data-testid="loadingbutton-button"
    >
      {children}
      {loading && (
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            paddingLeft: "5px",
          }}
        >
          <CircularProgress
            color="inherit"
            size={16}
            data-testid="loadingbutton-progress"
          />
        </Box>
      )}
    </Button>
  );
};
