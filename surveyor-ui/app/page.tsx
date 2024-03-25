"use client";

import { App } from "@/components/App";
import { theme } from "@/themes/themes.styles";
import { ThemeProvider } from "@mui/material";

export default function Main() {
  return (
    <ThemeProvider theme={theme}>
      <App />
    </ThemeProvider>
  );
}
