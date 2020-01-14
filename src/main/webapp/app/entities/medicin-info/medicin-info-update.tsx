import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './medicin-info.reducer';
import { IMedicinInfo } from 'app/shared/model/medicin-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMedicinInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicinInfoUpdate = (props: IMedicinInfoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { medicinInfoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/medicin-info' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.refillInfo = convertDateTimeToServer(values.refillInfo);

    if (errors.length === 0) {
      const entity = {
        ...medicinInfoEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicationBackendApp.medicinInfo.home.createOrEditLabel">
            <Translate contentKey="medicationBackendApp.medicinInfo.home.createOrEditLabel">Create or edit a MedicinInfo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : medicinInfoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="medicin-info-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="medicin-info-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="medicin-info-name">
                  <Translate contentKey="medicationBackendApp.medicinInfo.name">Name</Translate>
                </Label>
                <AvField id="medicin-info-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="importantInfoLabel" for="medicin-info-importantInfo">
                  <Translate contentKey="medicationBackendApp.medicinInfo.importantInfo">Important Info</Translate>
                </Label>
                <AvField id="medicin-info-importantInfo" type="text" name="importantInfo" />
              </AvGroup>
              <AvGroup>
                <Label id="usageLabel" for="medicin-info-usage">
                  <Translate contentKey="medicationBackendApp.medicinInfo.usage">Usage</Translate>
                </Label>
                <AvField id="medicin-info-usage" type="text" name="usage" />
              </AvGroup>
              <AvGroup>
                <Label id="initialCountLabel" for="medicin-info-initialCount">
                  <Translate contentKey="medicationBackendApp.medicinInfo.initialCount">Initial Count</Translate>
                </Label>
                <AvField id="medicin-info-initialCount" type="text" name="initialCount" />
              </AvGroup>
              <AvGroup>
                <Label id="promisedLabel" for="medicin-info-promised">
                  <Translate contentKey="medicationBackendApp.medicinInfo.promised">Promised</Translate>
                </Label>
                <AvField id="medicin-info-promised" type="text" name="promised" />
              </AvGroup>
              <AvGroup>
                <Label id="refillInfoLabel" for="medicin-info-refillInfo">
                  <Translate contentKey="medicationBackendApp.medicinInfo.refillInfo">Refill Info</Translate>
                </Label>
                <AvInput
                  id="medicin-info-refillInfo"
                  type="datetime-local"
                  className="form-control"
                  name="refillInfo"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.medicinInfoEntity.refillInfo)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="pharmacyNotesLabel" for="medicin-info-pharmacyNotes">
                  <Translate contentKey="medicationBackendApp.medicinInfo.pharmacyNotes">Pharmacy Notes</Translate>
                </Label>
                <AvField id="medicin-info-pharmacyNotes" type="text" name="pharmacyNotes" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/medicin-info" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  medicinInfoEntity: storeState.medicinInfo.entity,
  loading: storeState.medicinInfo.loading,
  updating: storeState.medicinInfo.updating,
  updateSuccess: storeState.medicinInfo.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicinInfoUpdate);
