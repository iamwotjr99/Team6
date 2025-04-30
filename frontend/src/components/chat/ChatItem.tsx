'use client';

import { useRouter, useSearchParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import BackHeader from '@/components/layout/header/BackHeader';
import YouMessage from '@/components/chat/YouMessage';
import MeMessage from '@/components/chat/MeMessage';
import Inputbar from '@/components/chat/Inputbar';
import Datebar from '@/components/chat/Datebar';
import ChatItemSkeleton from '@/components/chat/ChatItemSkeleton';

export default function ChatItem() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [title, setTitle] = useState<string | null>(null);

  useEffect(() => {
    const searchTitle = searchParams.get('title');
    if (searchTitle) {
      setTitle(searchTitle);
    }
  }, [searchParams]);

  const handleBack = () => {
    router.push('/chat');
  };

  // title이 아직 준비되지 않았다면 스켈레톤 UI 보여주기
  if (title === null) return <ChatItemSkeleton />;

  const messages = [
    { type: 'me', text: '안녕?', time: '10:01', date: '2025-04-15' },
    { type: 'you', nickname: '아이유', text: '안녕하세요!', time: '10:02', date: '2025-04-15' },
    { type: 'me', text: '그래', time: '10:05', date: '2025-04-16' },
    { type: 'you', nickname: '박보검', text: '넵', time: '18:09', date: '2025-04-18' },
  ];

  let lastDate = '';

  return (
    <section className="h-full overflow-y-auto">
      <BackHeader mainText={title} onClick={handleBack} />
      <div className="px-1 py-1">
        {messages.map((msg, idx) => {
          const isNewDate = msg.date !== lastDate;
          lastDate = msg.date;

          const dateLabel = new Date(msg.date).toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
          });

          return (
            <div key={idx}>
              {isNewDate && <Datebar date={dateLabel} />}
              {msg.type === 'me' ? (
                <MeMessage message={msg.text} time={msg.time} />
              ) : (
                <YouMessage nickname={msg.nickname!} message={msg.text} time={msg.time} />
              )}
            </div>
          );
        })}
      </div>
      <Inputbar />
    </section>
  );
}