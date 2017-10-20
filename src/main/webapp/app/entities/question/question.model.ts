import { BaseEntity } from './../../shared';

export class Question implements BaseEntity {
    constructor(
        public id?: number,
        public questionText?: string,
        public answers?: BaseEntity[],
    ) {
    }
}
