import 'react-notion-x/src/styles.css';
import 'prismjs/themes/prism-tomorrow.css';
import 'katex/dist/katex.min.css';

import NotionRender from '@/components/mypage/NotionRender';
import { getData } from '@/components/mypage/notion';
import Link from 'next/link';
import { IoChevronBack } from 'react-icons/io5';

export default async function Page() {
  const pageId = process.env.NEXT_PUBLIC_NOTION_TERMS_OF_SERVICE;
  if (!pageId) {
    throw new Error('노션에러');
  }

  const data = await getData(pageId);

  return (
    <>
      <header className='sticky top-0 z-50 flex w-full items-center justify-center border-b border-gray-300  bg-white  px-4 py-[16.5px] transition-colors duration-500'>
        <Link
          className='absolute left-1 h-8 w-1/6 font-medium bg-white  rounded-full flex items-center justify-center'
          href='/mypage'
        >
          <IoChevronBack size={35} color='black' />
        </Link>
        <h1 className='text-xl font-bold text-black'>이용약관</h1>
      </header>
      <section className='relative flex flex-col h-full'>
        <div className='flex flex-col overflow-y-auto'>
          <NotionRender recordMap={data} rootPageId={pageId} />
        </div>
      </section>
    </>
  );
}
