'use client';

import { useRouter, useSearchParams } from 'next/navigation';
import BackHeader from '@/components/layout/header/BackHeader';
import YouMessage from '@/components/chat/YouMessage';
import MeMessage from '@/components/chat/MeMessage';
import Inputbar from '@/components/chat/Inputbar';
import Datebar from '@/components/chat/Datebar';

export default function ChatItem() {
  const router = useRouter();
  const searchParams = useSearchParams();

  const title = searchParams.get('title') ?? '채팅방';

  const handleBack = () => {
    router.push('/chat');
  };

  const messages = [
    { type: 'me', text: '안녕?', time: '10:01', date: '2025-04-15' },
    { type: 'you', nickname:'아이유', text: '안녕하세요!', time: '10:02', date: '2025-04-15' },
    { type: 'me', text: '그래', time: '10:05', date: '2025-04-16' },
    { type: 'you', nickname:'박보검', text: '넵', time: '18:09', date: '2025-04-18' },
  ];

  let lastDate = '';

  return (
    <section className='h-full overflow-y-auto'>
      <BackHeader mainText={title} onClick={handleBack} />
      <div className='px-1 py-1'>
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
