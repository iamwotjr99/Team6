'use client';
import {useRouter} from 'next/navigation';

type Props = {
  id: string;
  title: string;
  lastMessage: string;
  updatedAt: string;
};

export default function Chatview({ id, title, lastMessage, updatedAt }: Props) {
  const router = useRouter();

  const handleClick = () => {
    router.push(`/chat/${id}?title=${encodeURIComponent(title)}`);
  };

  return (
    <div
      onClick={handleClick}
      className="cursor-pointer px-4 py-2.5 border-b border-gray-200"
    >
      <div className="flex justify-between items-center mb-1">
        <h2 className="text-lg font-semibold text-gray-800">{title}</h2>
        <span className="text-sm text-gray-400">{updatedAt}</span>
      </div>
      <p className="text-base text-gray-600 truncate">{lastMessage}</p>
    </div>
  );
}