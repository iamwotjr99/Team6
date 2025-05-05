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

  if (title === null) return <ChatItemSkeleton />;

  const messages = [
    { type: 'me', text: '안녕?', createdAt: '2025-04-15T10:01:00Z' },
    {
      type: 'you',
      nickname: '아이유',
      text: '안녕하세요!',
      createdAt: '2025-04-15T10:02:00Z',
    },
    { type: 'me', text: '그래', createdAt: '2025-04-16T10:05:00Z' },
    {
      type: 'you',
      nickname: '박보검',
      text: '넵',
      createdAt: '2025-04-18T18:09:00Z',
    },
  ];

  let lastDate = '';

  return (
    <section className='h-full overflow-y-auto'>
      <BackHeader mainText={title} onClick={handleBack} />
      <div className='px-1 py-1'>
        {messages.map((msg, idx) => {
          const dateObj = new Date(msg.createdAt);
          const date = dateObj.toISOString().split('T')[0];
          const time = dateObj.toTimeString().slice(0, 5);

          const isNewDate = date !== lastDate;
          lastDate = date;

          const dateLabel = dateObj.toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
          });

          return (
            <div key={idx}>
              {isNewDate && <Datebar date={dateLabel} />}
              {msg.type === 'me' ? (
                <MeMessage message={msg.text} time={time} />
              ) : (
                <YouMessage
                  nickname={msg.nickname!}
                  message={msg.text}
                  time={time}
                />
              )}
            </div>
          );
        })}
      </div>
      <Inputbar />
    </section>
  );
}
