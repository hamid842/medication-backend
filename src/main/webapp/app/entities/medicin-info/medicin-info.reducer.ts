import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMedicinInfo, defaultValue } from 'app/shared/model/medicin-info.model';

export const ACTION_TYPES = {
  FETCH_MEDICININFO_LIST: 'medicinInfo/FETCH_MEDICININFO_LIST',
  FETCH_MEDICININFO: 'medicinInfo/FETCH_MEDICININFO',
  CREATE_MEDICININFO: 'medicinInfo/CREATE_MEDICININFO',
  UPDATE_MEDICININFO: 'medicinInfo/UPDATE_MEDICININFO',
  DELETE_MEDICININFO: 'medicinInfo/DELETE_MEDICININFO',
  RESET: 'medicinInfo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMedicinInfo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MedicinInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: MedicinInfoState = initialState, action): MedicinInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEDICININFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDICININFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDICININFO):
    case REQUEST(ACTION_TYPES.UPDATE_MEDICININFO):
    case REQUEST(ACTION_TYPES.DELETE_MEDICININFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MEDICININFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDICININFO):
    case FAILURE(ACTION_TYPES.CREATE_MEDICININFO):
    case FAILURE(ACTION_TYPES.UPDATE_MEDICININFO):
    case FAILURE(ACTION_TYPES.DELETE_MEDICININFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICININFO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICININFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDICININFO):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDICININFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDICININFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/medicin-infos';

// Actions

export const getEntities: ICrudGetAllAction<IMedicinInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICININFO_LIST,
    payload: axios.get<IMedicinInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMedicinInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICININFO,
    payload: axios.get<IMedicinInfo>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMedicinInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDICININFO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMedicinInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDICININFO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMedicinInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDICININFO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
