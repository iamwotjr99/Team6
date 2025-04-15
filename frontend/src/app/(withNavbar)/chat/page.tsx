import SubHeader from '@/components/layout/header/SubHeader';
import ChatPreview from '@/components/chat/ChatPreview';

export default function Page() {
  const chatPreview = [
    {
      id: '1',
      title: '나와의 채팅',
      lastMessage: '링크 공유',
      updatedAt: '18:09',
    },
    {
      id: '2',
      title: '재석이와 대화',
      lastMessage: '그래',
      updatedAt: '10:05',
    },
    {
      id: '3',
      title: '팀 프로젝트',
      lastMessage: '회의는 오후 2시입니다.',
      updatedAt: '어제',
    },
    {
      id: '4',
      title: '수민이와 대화',
      lastMessage: '지금 전화 가능해?',
      updatedAt: '어제 ',
    },
    {
      id: '5',
      title: '스터디 모임',
      lastMessage: '자료 업로드했어요!',
      updatedAt: '4월 10일 (목)',
    },
  ];

  return (
    <>
      <SubHeader text="채팅 목록" />
      <section className="h-full overflow-y-auto pb-16">
        {chatPreview.map((chat) => (
          <ChatPreview
            key={chat.id}
            id={chat.id}
            title={chat.title}
            lastMessage={chat.lastMessage}
            updatedAt={chat.updatedAt}
          />
        ))}
      </section>
    </>
  );
}