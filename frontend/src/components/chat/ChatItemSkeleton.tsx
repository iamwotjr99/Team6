'use client';

export default function ChatItemSkeleton() {
  return (
    <section className="h-full flex flex-col animate-pulse">
      {/* 헤더 스켈레톤 */}
      <div className="h-14 bg-gray-200 flex items-center justify-center px-4">
        <div className="h-6 w-32 bg-gray-300 rounded" />
      </div>

      {/* 스크롤 되는 메시지 영역 */}
      <div className="flex-1 overflow-y-auto flex flex-col gap-3 px-2 py-2">
        {/* 날짜바 */}
        <div className="flex justify-center">
          <div className="h-4 w-36 bg-gray-200 rounded-full" />
        </div>

        {/* 내 메시지 */}
        <div className="flex justify-end">
          <div className="h-8 w-36 bg-gray-200 rounded-2xl" />
        </div>

        {/* 상대방 메시지 */}
        <div className="flex items-center gap-2">
          <div className="h-8 w-40 bg-gray-200 rounded-2xl" />
        </div>

        {/* 내 메시지 */}
        <div className="flex justify-end">
          <div className="h-8 w-28 bg-gray-200 rounded-2xl" />
        </div>

        {/* 상대방 메시지 */}
        <div className="flex items-center gap-2">
          <div className="h-8 w-28 bg-gray-200 rounded-2xl" />
        </div>
      </div>

      {/* 입력창 스켈레톤 */}
      <div className="h-16 border-t px-4 py-2 flex items-center">
        <div className="h-10 w-full bg-gray-200 rounded-full" />
      </div>
    </section>
  );
}