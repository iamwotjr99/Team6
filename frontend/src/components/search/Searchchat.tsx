'use client';

import { useRouter } from 'next/navigation';
import { useEffect, useState, useRef } from 'react';
import Searchbar from './Searchbar';
import BackHeader from '../layout/header/BackHeader';
import { fetchWeChat } from '@/app/api/chat';
import SearchPreview from './SearchPreview';
import ChatPreviewSkeleton from '../chat/ChatPreviewSkeleton';

export default function Searchchat() {
  const router = useRouter();
  const [query, setQuery] = useState('');
  const [allChats, setAllChats] = useState<any[]>([]);
  const [filteredChats, setFilteredChats] = useState<any[] | null>(null);
  const [loading, setLoading] = useState(false);
  const debounceRef = useRef<NodeJS.Timeout | null>(null);

  useEffect(() => {
    const loadChats = async () => {
      try {
        const chatList = await fetchWeChat();
        setAllChats(chatList);
      } catch (err) {
        console.error(err);
      }
    };
    loadChats();
  }, []);

  // 디바운싱으로 불필요한 호출 제거
  useEffect(() => {
    if (debounceRef.current) clearTimeout(debounceRef.current);
    
    debounceRef.current = setTimeout(() => {
      if (!query.trim()) {
        setFilteredChats(null);
        return;
      }
      const normalizedQuery = query.trim().toLowerCase();
      const filtered = allChats.filter((chat) =>
        chat.title.toLowerCase().includes(normalizedQuery)
      );
      setFilteredChats(filtered);
    }, 400); 
  }, [query, allChats]);

  const handleSearch = () => {
    if (!query.trim()) {
      setFilteredChats(null);
      return;
    }

    setLoading(true);
    const normalizedQuery = query.trim().toLowerCase();
    const filtered = allChats.filter((chat) =>
      chat.title.toLowerCase().includes(normalizedQuery)
    );
    setFilteredChats(filtered);
    setLoading(false);
  };

  const handleBack = () => router.back();

  return (
    <>
      <BackHeader mainText="검색" onClick={handleBack} />
      <Searchbar
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        onSearch={handleSearch}
      />

      <section className="h-full overflow-y-auto">
        {filteredChats === null ? (
          <p className="text-center text-gray-400 mt-6">검색어를 입력해주세요.</p>
        ) : loading ? (
          Array.from({ length: 6 }).map((_, i) => <ChatPreviewSkeleton key={i} />)
        ) : filteredChats.length === 0 ? (
          <p className="text-center text-gray-500 mt-4">검색 결과가 없습니다.</p>
        ) : (
          filteredChats.map((chat) => (
            <SearchPreview
              key={chat.id}
              id={chat.id.toString()}
              title={chat.title}
              lastMessage={chat.lastMessage ?? '대화를 시작해보세요.'}
              lastMessageAt={
                chat.lastMessageAt
                  ? new Date(chat.lastMessageAt).toLocaleString('ko-KR', {
                      year: 'numeric',
                      month: '2-digit',
                      day: '2-digit',
                      hour: '2-digit',
                      minute: '2-digit',
                    })
                  : '시간 정보 없음'
              }
            />
          ))
        )}
      </section>
    </>
  );
}