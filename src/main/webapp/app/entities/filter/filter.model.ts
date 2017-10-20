import { BaseEntity } from './../../shared';

export class Filter implements BaseEntity {
    constructor(
        public id?: number,
        public probability?: number,
        public answerId?: number,
        public criteria?: BaseEntity[],
    ) {
    }
}
