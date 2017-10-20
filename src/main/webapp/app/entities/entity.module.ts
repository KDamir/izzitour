import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EztourPlaceModule } from './place/place.module';
import { EztourCategoryModule } from './category/category.module';
import { EztourQuestionModule } from './question/question.module';
import { EztourAnswerModule } from './answer/answer.module';
import { EztourFilterModule } from './filter/filter.module';
import { EztourCriterionModule } from './criterion/criterion.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EztourPlaceModule,
        EztourCategoryModule,
        EztourQuestionModule,
        EztourAnswerModule,
        EztourFilterModule,
        EztourCriterionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EztourEntityModule {}
