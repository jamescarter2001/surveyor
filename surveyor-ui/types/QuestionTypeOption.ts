/**
 * Represents an option associated with a question type.
 */
export interface QuestionTypeOption {
  questionTypeOptionId: number;
  questionTypeId: number;
  value: number;
  alias?: string;
}
