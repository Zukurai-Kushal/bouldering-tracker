import { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../context/AuthContext';
import { getUserClimbs, searchUserClimbs } from '../api/userApi';
import ClimbCard from '../components/climbs/ClimbCard';
import LoginForm from '../components/auth/LoginForm';
import SettingsDropdown from '../components/common/SettingsDropdown';
import FilterModal from '../components/filters/FilterModal';
import { FunnelIcon, Cog6ToothIcon } from '@heroicons/react/24/outline';
import ClimbForm from '../components/climbs/ClimbForm';


export default function PrivatePage() {
  const { user } = useContext(AuthContext);
  const [climbs, setClimbs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showSettings, setShowSettings] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);
  const [filters, setFilters] = useState({});
  const [showClimbForm, setShowClimbForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [editClimb, setEditClimb] = useState(null);

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
        setClimbs(Array.isArray(res.data) ? res.data : []); // ✅ Ensure array
      } catch (err) {
        setError('Failed to load climbs. Please try again.');
        setClimbs([]); // ✅ Fallback to empty array
      } finally {
        setLoading(false);
      }
    };
      fetchData();
    }
  }, [user, filters]);

  if (!user) {
    return (
      <div className="p-4 pb-16">
        <LoginForm />
      </div>
    );
  }

  return (
    <div className="p-4 pb-16">
      {/* Header */}
      <header className="flex justify-between items-center mb-4">
        <div className="flex gap-2">
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
        </div>
        <div className="relative">
          <button
            onClick={() => setShowSettings(!showSettings)}
            className="flex items-center gap-1 bg-gray-200 dark:bg-gray-700 px-3 py-2 rounded-md"
          >
            <Cog6ToothIcon className="h-5 w-5" /> Settings
          </button>
          {showSettings && <SettingsDropdown />}
        </div>
      </header>

      {/* Filter Chips */}
      <div className="flex flex-wrap gap-2 mb-4">
        {filters.locationName && (
          <span className="bg-gray-300 dark:bg-gray-600 px-2 py-1 rounded-full text-sm">
            Location: {filters.locationName}
          </span>
        )}
        {filters.country && (
          <span className="bg-gray-300 dark:bg-gray-600 px-2 py-1 rounded-full text-sm">
            Country: {filters.country}
          </span>
        )}
        {filters.region && (
          <span className="bg-gray-300 dark:bg-gray-600 px-2 py-1 rounded-full text-sm">
            Region: {filters.region}
          </span>
        )}
        {(filters.latStart || filters.latEnd || filters.longStart || filters.longEnd) && (
          <span className="bg-gray-300 dark:bg-gray-600 px-2 py-1 rounded-full text-sm">
            GPS Range
          </span>
        )}
      </div>

      {/* Loading State */}
      {loading && (
        <div className="flex justify-center items-center h-40">
          <svg className="animate-spin h-8 w-8 text-blue-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
            <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8H4z"></path>
          </svg>
        </div>
      )}

      {/* Error State */}
      {error && !loading && (
        <div className="text-red-500 text-center mb-4">{error}</div>
      )}

      {/* Empty State */}
      {!loading && !error && climbs.length === 0 && (
        <div className="text-center text-gray-500">No climbs found.</div>
      )}

      {/* Climbs List */}
      {!loading && !error && climbs.length > 0 && (
        <div className="space-y-4">
          {climbs.map(climb => (            
            <ClimbCard
              key={climb.climbId}
              climb={climb}
              isPrivate={true}
              onEdit={(climb) => {
                setEditClimb(climb);
                setShowEditForm(true);
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