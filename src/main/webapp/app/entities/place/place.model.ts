import { BaseEntity } from './../../shared';

export class Place implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public avgPrice?: number,
        public lat?: string,
        public lon?: string,
        public startWorkTime?: string,
        public endWorkTime?: string,
        public categories?: BaseEntity[],
    ) {
    }
}
