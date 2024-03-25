import { FC, useEffect, useState } from "react";
import {
  Box,
  Card,
  CardActionArea,
  CardContent,
  CardHeader,
  CardMedia,
  Grow,
  Typography,
} from "@mui/material";

type PictureCardProps = {
  title: string;
  subtitle?: string;
  image: string;
  content: string;
  onClick?: () => void;
};

const styles = {
  card: {
    transition: "transform 0.3s ease",
    "&:hover": {
      transform: "scale(1.02)",
    },
  },
};

/**
 * Custom card component with titles, an image and a body.
 * @param title A string containing the title of the Card component.
 * @param subtitle A string containing the subtitle of the Card component.
 * @param image A string containing the image url for the Card component.
 * @param content A string containing the main body of the Card component
 * @param onClick A callback function to execute when the Card component is clicked.
 * @returns A PictureCard React component.
 */
export const PictureCard: FC<PictureCardProps> = ({
  title,
  subtitle,
  image,
  content,
  onClick,
}) => {
  const [show, setShow] = useState<boolean>(false);

  useEffect(() => {
    setShow(true);
  }, []);

  return (
    <>
      <Box sx={styles.card}>
        <Grow in={show}>
          <Card elevation={3} data-testid="pc-card">
            <CardActionArea onClick={onClick} data-testid="pc-cardactionarea">
              <CardHeader
                title={title}
                subheader={subtitle}
                data-testid="pc-cardheader"
              />
              <CardMedia component="img" height="194" image={image} />
              <CardContent style={{ height: "50px" }}>
                <Typography variant="body2" color="text.secondary">
                  {content}
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grow>
      </Box>
    </>
  );
};
