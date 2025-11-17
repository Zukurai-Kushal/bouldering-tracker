import { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../context/AuthContext';
import { FilterContext } from '../context/FilterContext';
import { getUserClimbs, searchUserClimbs } from '../api/userApi';
import ClimbCard from '../components/climbs/ClimbCard';
import LoginForm from '../components/auth/LoginForm';
import RegisterForm from '../components/auth/RegisterForm';
import SettingsDropdown from '../components/common/SettingsDropdown';
import FilterModal from '../components/filters/FilterModal';
import HeaderBar from '../components/common/HeaderBar';
import ClimbForm from '../components/climbs/ClimbForm';
import { useLocation } from 'react-router-dom';

export default function PrivatePage() {
  const { user } = useContext(AuthContext);
  const { filters, setFilters, getCleanFilters } = useContext(FilterContext);
  const [climbs, setClimbs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showSettings, setShowSettings] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);
  const [showClimbForm, setShowClimbForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [editClimb, setEditClimb] = useState(null);
  const [showRegister, setShowRegister] = useState(false);
  const location = useLocation();
  
  useEffect(() => {
    if (location.state?.openAddClimb) {
      setShowClimbForm(true);
    }
  }, [location.state]);

  useEffect(() => {
    if (user) {
      setLoading(true);
      setError('');
      const fetchData = async () => {
        try {
          const params = getCleanFilters();
          const res = Object.keys(params).length
            ? await searchUserClimbs(params)
            : await getUserClimbs();
          setClimbs(Array.isArray(res.data) ? res.data : []);
        } catch {
          setError('Failed to load climbs. Please try again.');
          setClimbs([]);
        } finally {
          setLoading(false);
        }
      };
      fetchData();
    }
  }, [user, filters]);

  if (!user) {
    return (
      <div className="p-4 flex items-center justify-center h-screen">
        {showRegister ? (
          <RegisterForm onSwitchToLogin={() => setShowRegister(false)} />
        ) : (
          <LoginForm onSwitchToRegister={() => setShowRegister(true)} />
        )}
      </div>
    );
  }

  return (
    <div className="p-4">
      <HeaderBar
        filters={filters}
        onFilterClick={() => setShowFilterModal(true)}
        onSettingsClick={() => setShowSettings(!showSettings)}
        showSettings={showSettings}
        children={{ onAddClimb: () => setShowClimbForm(true) }}
      />
      {showSettings && <SettingsDropdown />}

      {loading && <div>Loading climbs...</div>}
      {error && !loading && <div className="text-red-500">{error}</div>}
      {!loading && !error && climbs.length === 0 && <div>No climbs found.</div>}

      {!loading && !error && climbs.length > 0 && (
        <div className="flex flex-col items-center gap-6 w-full px-4">
          {climbs.map(climb => (
            <ClimbCard
              key={climb.climbId}
              climb={climb}
              isPrivate={true}
              onEdit={() => {
                setEditClimb(climb);
                setShowEditForm(true);
              }}
              onDeleteSuccess={async () => {
                const res = await getUserClimbs();
                setClimbs(res.data);
              }}
            />
          ))}
        </div>
      )}

      {showFilterModal && (
        <FilterModal
          onClose={() => setShowFilterModal(false)}
          onApply={newFilters => setFilters(newFilters)}
          currentFilters={filters}
        />
      )}
      {showClimbForm && (
        <ClimbForm
          onClose={() => setShowClimbForm(false)}
          onSuccess={() => {
            setShowClimbForm(false);
            getUserClimbs().then(res => setClimbs(res.data));
          }}
        />
      )}
      {showEditForm && (
        <ClimbForm
          mode="edit"
          initialData={editClimb}
          climbId={editClimb.climbId}
          onClose={() => setShowEditForm(false)}
          onSuccess={() => {
            setShowEditForm(false);
            getUserClimbs().then(res => setClimbs(res.data));
          }}
        />
      )}
    </div>
  );
}