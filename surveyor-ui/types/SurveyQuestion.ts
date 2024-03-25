import { Question } from "./Question";

/**
 * Represents a survey question connection.
 */
export interface SurveyQuestion {
    priority: number;
    question: Question;
}