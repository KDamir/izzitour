import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EztourSharedModule } from '../../shared';
import {
    CategoryService,
    CategoryPopupService,
    CategoryComponent,
    CategoryDetailComponent,
    CategoryDialogComponent,
    CategoryPopupComponent,
    CategoryDeletePopupComponent,
    CategoryDeleteDialogComponent,
    categoryRoute,
    categoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...categoryRoute,
    ...categoryPopupRoute,
];

@NgModule({
    imports: [
        EztourSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CategoryComponent,
        CategoryDetailComponent,
        CategoryDialogComponent,
        CategoryDeleteDialogComponent,
        CategoryPopupComponent,
        CategoryDeletePopupComponent,
    ],
    entryComponents: [
        CategoryComponent,
        CategoryDialogComponent,
        CategoryPopupComponent,
        CategoryDeleteDialogComponent,
        CategoryDeletePopupComponent,
    ],
    providers: [
        CategoryService,
        CategoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EztourCategoryModule {}
