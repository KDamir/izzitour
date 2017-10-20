import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CriterionComponent } from './criterion.component';
import { CriterionDetailComponent } from './criterion-detail.component';
import { CriterionPopupComponent } from './criterion-dialog.component';
import { CriterionDeletePopupComponent } from './criterion-delete-dialog.component';

export const criterionRoute: Routes = [
    {
        path: 'criterion',
        component: CriterionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eztourApp.criterion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'criterion/:id',
        component: CriterionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eztourApp.criterion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const criterionPopupRoute: Routes = [
    {
        path: 'criterion-new',
        component: CriterionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eztourApp.criterion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'criterion/:id/edit',
        component: CriterionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eztourApp.criterion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'criterion/:id/delete',
        component: CriterionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eztourApp.criterion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
