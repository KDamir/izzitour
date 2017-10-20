import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Criterion } from './criterion.model';
import { CriterionService } from './criterion.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-criterion',
    templateUrl: './criterion.component.html'
})
export class CriterionComponent implements OnInit, OnDestroy {
criteria: Criterion[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private criterionService: CriterionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.criterionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.criteria = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCriteria();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Criterion) {
        return item.id;
    }
    registerChangeInCriteria() {
        this.eventSubscriber = this.eventManager.subscribe('criterionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
