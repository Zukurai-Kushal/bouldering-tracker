import { useEffect, useState } from 'react';
import { getSharedClimbs, searchSharedClimbs } from '../api/publicApi';
import ClimbCard from '../components/climbs/ClimbCard';
import FilterModal from '../components/filters/FilterModal';
import SettingsDropdown from '../components/common/SettingsDropdown';
import { FunnelIcon, Cog6ToothIcon } from '@heroicons/react/24/outline';

export default function PublicPage() {
  const [climbs, setClimbs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);
  const [showSettings, setShowSettings] = useState(false);
  const [filters, setFilters] = useState({});

  // Fetch climbs (initial or filtered)
  useEffect(() => {
    setLoading(true);
    const fetchData = async () => {
      try {
        if (Object.keys(filters).length === 0) {
          const res = await getSharedClimbs();
          setClimbs(res.data);
        } else {
          const params = {};
          if (filters.locationId) params.locationId = filters.locationId;
          if (filters.country) params.country = filters.country;
          if (filters.region) params.region = filters.region;
          if (filters.latStart) params.latStart = filters.latStart;
          if (filters.latEnd) params.latEnd = filters.latEnd;
          if (filters.longStart) params.longStart = filters.longStart;
          if (filters.longEnd) params.longEnd = filters.longEnd;

          const res = await searchSharedClimbs(params);
          setClimbs(res.data);
        }
      } catch (error) {
        console.error('Error fetching climbs:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [filters]);

  return (
    <div className="p-4 pb-16">
      {/* Header */}
      <header className="flex justify-between items-center mb-4">
        <button
          onClick={() => setShowFilterModal(true)}
          className="flex items-center gap-1 bg-blue-500 text-white px-3 py-2 rounded-md"
        >
          <FunnelIcon className="h-5 w-5" /> Filter
        </button>
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
      {loading ? (
        <div className="flex justify-center items-center h-40">
          <svg className="animate-spin h-8 w-8 text-blue-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
            <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8H4z"></path>
          </svg>
        </div>
      ) : (
        <div className="flex flex-col items-center gap-4 w-full px-4">
          {climbs.map(climb => (
            <ClimbCard key={climb.id} climb={climb} isPrivate={false} />
          ))}
        </div>
      )}

      {climbs.length === 0 && !loading && (
        <p className="text-center text-gray-500">No climbs found. Try adjusting filters.</p>
      )}

      {/* Filter Modal */}
      {showFilterModal && (
        <FilterModal
          onClose={() => setShowFilterModal(false)}
          onApply={(newFilters) => setFilters(newFilters)}
          currentFilters={filters}
        />
      )}
    </div>
  );
}