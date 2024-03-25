import {
  Backdrop,
  Box,
  CircularProgress,
  Fade,
  IconButton,
  Modal,
  Theme,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { useForm, UseFormRegister } from "react-hook-form";
import { FC, ReactNode, useState } from "react";
import { LabelledTextField } from "../common/LabelledTextField";
import { Question } from "@/types/Question";
import { Facility } from "@/types/Facility";
import { Survey } from "@/types/Survey";
import { LoadingButton } from "../common/LoadingButton";
import { ResponseScale } from "./ResponseScale";

type SurveyModalProps = {
  open: boolean;
  survey?: Survey;
  facility: Facility;
  onClose?: () => void;
  loading?: boolean;
};

const modalStyle = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: (theme: Theme) => theme.breakpoints.values.lg,
  bgcolor: "background.paper",
  boxShadow: 24,
  padding: 4,
  display: "flex",
  flexDirection: "column",
  alignItems: "stretch",
  textAlign: "center",
};

/**
 * Helper function for rendering questions based on their type.
 * @param index A number containing the index of the question in the context of the rendered survey.
 * @param question A Question object containing the question to render.
 * @param register An optional react-hook-form register to pass updates to.
 * @param disabled A boolean dictating whether the rendered question response input is disabled.
 * @returns A ReactNode containing the rendered question.
 */
const renderQuestion = (
  index: number,
  question: Question,
  register: UseFormRegister<any>,
  disabled: boolean,
): ReactNode => {
  // Formats the question number and body into a single string.
  const questionBody = `${index + 1}. ${question.body}`;

  switch (question.type.name) {
    case "FreeText": {
      return (
        <LabelledTextField
          id={question.questionId.toString()}
          label={questionBody}
          disabled={disabled}
          register={register}
        />
      );
    }
    case "LikertScale": {
      return (
        <ResponseScale
          id={question.questionId.toString()}
          label={questionBody}
          options={question.type.options}
          disabled={disabled}
          register={register}
        />
      );
    }
    default: {
      return null;
    }
  }
};

/**
 * Survey modal component used for displaying survey questions and taking responses for a given facility.
 * @param open A boolean dictating whether the modal is open.
 * @param facility A Facility object.
 * @param onClose An optional callback function to execute when the modal is closed.
 * @returns A SurveyModal component.
 */
export const SurveyModal: FC<SurveyModalProps> = ({
  open,
  survey,
  facility,
  onClose,
  loading,
}) => {
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [success, setSuccess] = useState<boolean>(false);
  const [error, setError] = useState<string | undefined>(undefined);

  const {
    register,
    handleSubmit,
    reset,
    formState: { isDirty, isValid },
  } = useForm();

  /**
   * Function to send a POST request containing all of the provided response data to the
   * backend, executed when the user clicks the submit button in the modal.
   * @param data The form data provided by the user, forwarded by react-hook-form.
   */
  const onSubmit = (data: any) => {
    const payload = Object.entries(data).map((r) => {
      return {
        surveyId: survey?.surveyId,
        questionId: r[0],
        facilityId: facility.facilityId,
        response: r[1],
      };
    });

    setSubmitting(true);
    setError(undefined);

    fetch("http://localhost:8080/surveys/response", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    })
      .then(() => setSuccess(true))
      .catch((err) => {
        console.log(err);
        setError("Internal Server Error");
      })
      .finally(() => {
        setSubmitting(false);
      });
  };

  const handleClose = () => {
    onClose && onClose();

    setSubmitting(false);
    setSuccess(false);
    setError(undefined);

    reset();
  };

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      closeAfterTransition
      slots={{ backdrop: Backdrop }}
      slotProps={{
        backdrop: {
          timeout: 500,
        },
      }}
    >
      <Fade in={open}>
        <Box sx={modalStyle}>
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "right",
            }}
          >
            <IconButton data-testid="sm-button-close" onClick={handleClose}>
              <CloseIcon />
            </IconButton>
          </Box>
          <Typography variant="h3">Feedback Form</Typography>
          <Typography
            variant="subtitle1"
            sx={{ color: (theme: Theme) => theme.palette.text.secondary }}
          >
            {facility.name}
          </Typography>
          {loading && (
            <Box sx={{ padding: 3 }}>
              <CircularProgress data-testid="sm-progress" />
            </Box>
          )}
          <form onSubmit={handleSubmit(onSubmit)}>
            {survey &&
              survey?.connections
                .sort((a, b) => a.priority - b.priority)
                .map((connection, index) => (
                  <Box
                    key={connection.question.questionId}
                    sx={{
                      paddingTop: 1,
                      paddingBottom: 1,
                      alignItems: "stretch",
                    }}
                  >
                    {renderQuestion(
                      index,
                      connection.question,
                      register,
                      submitting || success,
                    )}
                  </Box>
                ))}
            <Box sx={{ paddingTop: 2, alignItems: "center" }}>
              <LoadingButton
                variant="contained"
                type="submit"
                disabled={!isDirty || !isValid || submitting || success}
                loading={submitting}
              >
                Submit
              </LoadingButton>
            </Box>
            <Box sx={{ paddingTop: 2 }}>
              {success && (
                <Typography variant="subtitle1">
                  Thanks for your feedback!
                </Typography>
              )}
              {error && <Typography variant="subtitle1">{error}</Typography>}
            </Box>
          </form>
        </Box>
      </Fade>
    </Modal>
  );
};
