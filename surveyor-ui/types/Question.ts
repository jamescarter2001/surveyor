import { QuestionType } from "./QuestionType"

/**
 * Represents a question that can be asked to the user.
 */
export interface Question {
    questionId: number;
    body: string;
    type: QuestionType;
}