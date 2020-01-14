import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './medicin-info.reducer';
import { IMedicinInfo } from 'app/shared/model/medicin-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMedicinInfoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MedicinInfo = (props: IMedicinInfoProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  useEffect(() => {
    getAllEntities();
  }, []);

  const sortEntities = () => {
    getAllEntities();
    props.history.push(
      `${props.location.pathname}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`
    );
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage
    });

  const { medicinInfoList, match, totalItems } = props;
  return (
    <div>
      <h2 id="medicin-info-heading">
        <Translate contentKey="medicationBackendApp.medicinInfo.home.title">Medicin Infos</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="medicationBackendApp.medicinInfo.home.createLabel">Create new Medicin Info</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {medicinInfoList && medicinInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="medicationBackendApp.medicinInfo.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('importantInfo')}>
                  <Translate contentKey="medicationBackendApp.medicinInfo.importantInfo">Important Info</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('usage')}>
                  <Translate contentKey="medicationBackendApp.medicinInfo.usage">Usage</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('initialCount')}>
                  <Translate contentKey="medicationBackendApp.medicinInfo.initialCount">Initial Count</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('promised')}>
                  <Translate contentKey="medicationBackendApp.medicinInfo.promised">Promised</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('refillInfo')}>
                  <Translate contentKey="medicationBackendApp.medicinInfo.refillInfo">Refill Info</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pharmacyNotes')}>
                  <Translate contentKey="medicationBackendApp.medicinInfo.pharmacyNotes">Pharmacy Notes</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {medicinInfoList.map((medicinInfo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${medicinInfo.id}`} color="link" size="sm">
                      {medicinInfo.id}
                    </Button>
                  </td>
                  <td>{medicinInfo.name}</td>
                  <td>{medicinInfo.importantInfo}</td>
                  <td>{medicinInfo.usage}</td>
                  <td>{medicinInfo.initialCount}</td>
                  <td>{medicinInfo.promised}</td>
                  <td>
                    <TextFormat type="date" value={medicinInfo.refillInfo} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{medicinInfo.pharmacyNotes}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${medicinInfo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${medicinInfo.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${medicinInfo.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="medicationBackendApp.medicinInfo.home.notFound">No Medicin Infos found</Translate>
          </div>
        )}
      </div>
      <div className={medicinInfoList && medicinInfoList.length > 0 ? '' : 'd-none'}>
        <Row className="justify-content-center">
          <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
        </Row>
        <Row className="justify-content-center">
          <JhiPagination
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
        </Row>
      </div>
    </div>
  );
};

const mapStateToProps = ({ medicinInfo }: IRootState) => ({
  medicinInfoList: medicinInfo.entities,
  totalItems: medicinInfo.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicinInfo);
