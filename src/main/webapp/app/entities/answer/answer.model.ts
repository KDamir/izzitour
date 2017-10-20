import { BaseEntity } from './../../shared';

export class Answer implements BaseEntity {
    constructor(
        public id?: number,
        public result?: string,
        public questionId?: number,
        public filters?: BaseEntity[],
        public nextQuestions?: BaseEntity[],
    ) {
    }
}
