import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './medicin-info.reducer';
import { IMedicinInfo } from 'app/shared/model/medicin-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMedicinInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicinInfoDetail = (props: IMedicinInfoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { medicinInfoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="medicationBackendApp.medicinInfo.detail.title">MedicinInfo</Translate> [<b>{medicinInfoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="medicationBackendApp.medicinInfo.name">Name</Translate>
            </span>
          </dt>
          <dd>{medicinInfoEntity.name}</dd>
          <dt>
            <span id="importantInfo">
              <Translate contentKey="medicationBackendApp.medicinInfo.importantInfo">Important Info</Translate>
            </span>
          </dt>
          <dd>{medicinInfoEntity.importantInfo}</dd>
          <dt>
            <span id="usage">
              <Translate contentKey="medicationBackendApp.medicinInfo.usage">Usage</Translate>
            </span>
          </dt>
          <dd>{medicinInfoEntity.usage}</dd>
          <dt>
            <span id="initialCount">
              <Translate contentKey="medicationBackendApp.medicinInfo.initialCount">Initial Count</Translate>
            </span>
          </dt>
          <dd>{medicinInfoEntity.initialCount}</dd>
          <dt>
            <span id="promised">
              <Translate contentKey="medicationBackendApp.medicinInfo.promised">Promised</Translate>
            </span>
          </dt>
          <dd>{medicinInfoEntity.promised}</dd>
          <dt>
            <span id="refillInfo">
              <Translate contentKey="medicationBackendApp.medicinInfo.refillInfo">Refill Info</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={medicinInfoEntity.refillInfo} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="pharmacyNotes">
              <Translate contentKey="medicationBackendApp.medicinInfo.pharmacyNotes">Pharmacy Notes</Translate>
            </span>
          </dt>
          <dd>{medicinInfoEntity.pharmacyNotes}</dd>
        </dl>
        <Button tag={Link} to="/medicin-info" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medicin-info/${medicinInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ medicinInfo }: IRootState) => ({
  medicinInfoEntity: medicinInfo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicinInfoDetail);
