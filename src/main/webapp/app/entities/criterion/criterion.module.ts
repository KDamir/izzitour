import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EztourSharedModule } from '../../shared';
import {
    CriterionService,
    CriterionPopupService,
    CriterionComponent,
    CriterionDetailComponent,
    CriterionDialogComponent,
    CriterionPopupComponent,
    CriterionDeletePopupComponent,
    CriterionDeleteDialogComponent,
    criterionRoute,
    criterionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...criterionRoute,
    ...criterionPopupRoute,
];

@NgModule({
    imports: [
        EztourSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CriterionComponent,
        CriterionDetailComponent,
        CriterionDialogComponent,
        CriterionDeleteDialogComponent,
        CriterionPopupComponent,
        CriterionDeletePopupComponent,
    ],
    entryComponents: [
        CriterionComponent,
        CriterionDialogComponent,
        CriterionPopupComponent,
        CriterionDeleteDialogComponent,
        CriterionDeletePopupComponent,
    ],
    providers: [
        CriterionService,
        CriterionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EztourCriterionModule {}
