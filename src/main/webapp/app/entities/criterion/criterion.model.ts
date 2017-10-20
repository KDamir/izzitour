import { BaseEntity } from './../../shared';

export const enum TypeEnum {
    'LAT',
    'LON',
    'ACTIVE_TIME',
    'CATEGORY'
}

export class Criterion implements BaseEntity {
    constructor(
        public id?: number,
        public type?: TypeEnum,
        public maxValue?: string,
        public minValue?: string,
        public filterId?: number,
    ) {
    }
}
