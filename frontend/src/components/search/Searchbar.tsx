import Input from '@/components/common/Input';
import Button from '@/components/common/Button';

type Props = {
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onSearch: () => void;
};

export default function Searchbar({ value, onChange, onSearch }: Props) {
  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      onSearch();
    }
  };
  
  return (
    <div className="flex items-center gap-2 p-2 border-b border-gray-300">
      <Input
        type="search"
        value={value}
        onChange={onChange}
        onKeyDown={handleKeyDown}
        placeholder="검색어를 입력하세요"
        className="border rounded-sm h-12"
      />
      <Button
        onClick={onSearch}
        className="w-20 h-12 bg-green-700 text-white font-semibold" 
      >
        검색
      </Button>
    </div>
  );
}