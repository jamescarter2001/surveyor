import { SurveyQuestion } from "./SurveyQuestion";

/**
 * Represents a survey that can be presented to the user.
 */
export interface Survey {
    surveyId: number;
    name: string;
    startTime: Date;
    endTIme: Date;
    connections: SurveyQuestion[];
}