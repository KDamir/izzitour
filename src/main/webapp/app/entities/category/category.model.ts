import { BaseEntity } from './../../shared';

export const enum CategoryEnum {
    'CAFE',
    'RESTORAUNT',
    'MUSEUM'
}

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public value?: CategoryEnum,
        public placeId?: number,
    ) {
    }
}
