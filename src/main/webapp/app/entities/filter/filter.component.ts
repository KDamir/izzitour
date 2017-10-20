import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Filter } from './filter.model';
import { FilterService } from './filter.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-filter',
    templateUrl: './filter.component.html'
})
export class FilterComponent implements OnInit, OnDestroy {
filters: Filter[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private filterService: FilterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.filterService.query().subscribe(
            (res: ResponseWrapper) => {
                this.filters = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFilters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Filter) {
        return item.id;
    }
    registerChangeInFilters() {
        this.eventSubscriber = this.eventManager.subscribe('filterListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
