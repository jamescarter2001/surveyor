import { QuestionTypeOption } from "./QuestionTypeOption"

/**
 * Represents a question type.
 */
export interface QuestionType {
    questionTypeId: number;
    name: string;
    options: QuestionTypeOption[];
}