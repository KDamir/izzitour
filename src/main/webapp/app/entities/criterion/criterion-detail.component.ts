import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Criterion } from './criterion.model';
import { CriterionService } from './criterion.service';

@Component({
    selector: 'jhi-criterion-detail',
    templateUrl: './criterion-detail.component.html'
})
export class CriterionDetailComponent implements OnInit, OnDestroy {

    criterion: Criterion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private criterionService: CriterionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCriteria();
    }

    load(id) {
        this.criterionService.find(id).subscribe((criterion) => {
            this.criterion = criterion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCriteria() {
        this.eventSubscriber = this.eventManager.subscribe(
            'criterionListModification',
            (response) => this.load(this.criterion.id)
        );
    }
}
