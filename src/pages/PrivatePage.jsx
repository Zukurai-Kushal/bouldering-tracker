import { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../context/AuthContext';
import { getUserClimbs, searchUserClimbs } from '../api/userApi';
import ClimbCard from '../components/climbs/ClimbCard';
import LoginForm from '../components/auth/LoginForm';
import RegisterForm from '../components/auth/RegisterForm';
import SettingsDropdown from '../components/common/SettingsDropdown';
import FilterModal from '../components/filters/FilterModal';
import { FunnelIcon, Cog6ToothIcon } from '@heroicons/react/24/outline';
import ClimbForm from '../components/climbs/ClimbForm';

export default function PrivatePage() {
  const { user } = useContext(AuthContext);

  // State hooks (always at top level)
  const [climbs, setClimbs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showSettings, setShowSettings] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);
  const [filters, setFilters] = useState({});
  const [showClimbForm, setShowClimbForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [editClimb, setEditClimb] = useState(null);
  const [showRegister, setShowRegister] = useState(false);

  // Effect for fetching climbs when user is logged in
  useEffect(() => {
    if (user) {
      setLoading(true);
      setError('');
      const fetchData = async () => {
        try {
          let res;
          if (Object.keys(filters).length === 0) {
            res = await getUserClimbs();
          } else {
            const params = {};
            if (filters.locationId) params.locationId = filters.locationId;
            if (filters.country) params.country = filters.country;
            if (filters.region) params.region = filters.region;
            if (filters.latStart) params.latStart = filters.latStart;
            if (filters.latEnd) params.latEnd = filters.latEnd;
            if (filters.longStart) params.longStart = filters.longStart;
            if (filters.longEnd) params.longEnd = filters.longEnd;
            res = await searchUserClimbs(params);
          }
          setClimbs(Array.isArray(res.data) ? res.data : []);
        } catch (err) {
          setError('Failed to load climbs. Please try again.');
          setClimbs([]);
        } finally {
          setLoading(false);
        }
      };
      fetchData();
    }
  }, [user, filters]);

  return (
    <div className="p-4">
      {/* If user is not logged in, show login/register */}
      {!user ? (
        showRegister ? (
          <RegisterForm onSwitchToLogin={() => setShowRegister(false)} />
        ) : (
          <LoginForm onSwitchToRegister={() => setShowRegister(true)} />
        )
      ) : (
        <>
          {/* Header */}
          <div className="flex gap-2 mb-4">
            <button
              onClick={() => setShowFilterModal(true)}
              className="flex items-center gap-1 bg-blue-500 text-white px-3 py-2 rounded-md"
            >
              <FunnelIcon className="h-5 w-5" /> Filter
            </button>
            <button
              onClick={() => setShowClimbForm(true)}
              className="flex items-center gap-1 bg-green-500 text-white px-3 py-2 rounded-md"
            >
              + Add Climb
            </button>
            <button
              onClick={() => setShowSettings(!showSettings)}
              className="flex items-center gap-1 bg-gray-200 dark:bg-gray-700 px-3 py-2 rounded-md"
            >
              <Cog6ToothIcon className="h-5 w-5" /> Settings
            </button>
            {showSettings && <SettingsDropdown />}
          </div>

          {/* Filter Chips */}
          <div className="mb-4">
            {filters.locationName && <span className="mr-2">Location: {filters.locationName}</span>}
            {filters.country && <span className="mr-2">Country: {filters.country}</span>}
            {filters.region && <span className="mr-2">Region: {filters.region}</span>}
            {(filters.latStart && filters.latEnd && filters.longStart && filters.longEnd) && (
              <span className="mr-2">GPS Range</span>
            )}
          </div>

          {/* Loading State */}
          {loading && <div>Loading climbs...</div>}

          {/* Error State */}
          {error && !loading && <div className="text-red-500">{error}</div>}

          {/* Empty State */}
          {!loading && !error && climbs.length === 0 && <div>No climbs found.</div>}

          {/* Climbs List */}
          {!loading && !error && climbs.length > 0 && (
            <div className="flex flex-col items-center gap-6 w-full px-4">
              {climbs.map((climb) => (
                <ClimbCard
                  key={climb.climbId}
                  climb={climb}
                  isPrivate={true}
                  onEdit={() => {
                    setEditClimb(climb);
                    setShowEditForm(true);
                  }}
                  onDeleteSuccess={async () => {
                    try {
                      const res = await getUserClimbs();
                      setClimbs(res.data);
                    } catch (err) {
                      setError('Failed to refresh climbs:');
                      console.error('Failed to refresh climbs:', err);
                    }
                  }}
                />
              ))}
            </div>
          )}

          {/* Filter Modal */}
          {showFilterModal && (
            <FilterModal
              onClose={() => setShowFilterModal(false)}
              onApply={(newFilters) => setFilters(newFilters)}
              currentFilters={filters}
            />
          )}

          {/* Add Climb Form */}
          {showClimbForm && (
            <ClimbForm
              onClose={() => setShowClimbForm(false)}
              onSuccess={() => {
                setShowClimbForm(false);
                getUserClimbs().then((res) => setClimbs(res.data));
              }}
            />
          )}

          {/* Edit Climb Form */}
          {showEditForm && (
            <ClimbForm
              mode="edit"
              initialData={editClimb}
              climbId={editClimb.climbId}
              onClose={() => setShowEditForm(false)}
              onSuccess={() => {
                setShowEditForm(false);
                getUserClimbs().then((res) => setClimbs(res.data));
              }}
            />
          )}
        </>
      )}
    </div>
  );
}