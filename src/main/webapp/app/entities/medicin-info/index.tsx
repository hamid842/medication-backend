import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MedicinInfo from './medicin-info';
import MedicinInfoDetail from './medicin-info-detail';
import MedicinInfoUpdate from './medicin-info-update';
import MedicinInfoDeleteDialog from './medicin-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MedicinInfoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MedicinInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MedicinInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MedicinInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={MedicinInfo} />
    </Switch>
  </>
);

export default Routes;
