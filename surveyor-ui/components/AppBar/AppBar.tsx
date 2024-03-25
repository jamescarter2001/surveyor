import { AppBar, styled } from "@mui/material";

/**
 * App bar component configured with the style of the Material UI theme.
 */
export default styled(AppBar)(({ theme }) => ({
  background: `linear-gradient(to right, ${theme.palette.primary.light} , ${theme.palette.primary.dark})`,
}));
